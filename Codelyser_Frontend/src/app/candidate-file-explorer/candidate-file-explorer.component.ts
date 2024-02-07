import { Component, OnInit, importProvidersFrom } from '@angular/core';
import { NestedTreeControl } from '@angular/cdk/tree';
import { MatTreeNestedDataSource } from '@angular/material/tree';
import { CandidatefileexplorerService } from '../services/candidatefileexplorer.service';
import { HttpClient } from '@angular/common/http';
import { CodefileService } from '../services/codefile.service';
import { map } from 'rxjs';
import { CandidatedataService } from '../services/candidatedata.service';
import { UserIDService } from '../services/user-id.service';
import { TrackchangefileService } from '../trackchangefile.service';
interface TreeNode {
  parent: null;
  name: string;
  children?: TreeNode[];
  path?: string;
  isFile?: boolean;
  hovered?: boolean;
}
@Component({
  selector: 'app-candidate-file-explorer',
  templateUrl: './candidate-file-explorer.component.html',
  styleUrl: './candidate-file-explorer.component.scss'
})
export class CandidateFileExplorerComponent implements OnInit {
  //  `${this.apiUrl}/${userid}`;

  private url = 'http://localhost:8080/candidate/project-structure';
  path: string = '';
  opened = false;
  apiUrl!:string;
  userId:string|null='';
  treeControl = new NestedTreeControl<TreeNode>((node) => node.children);
  dataSource = new MatTreeNestedDataSource<TreeNode>();
  selectedNode: TreeNode | null = null;
  projectName!:string;
  trackchangefile!:TrackchangefileService;
  previous:string="";
  hasChild = (_: number, node: TreeNode) => !!node.children && node.children.length > 0;
  constructor(private trackchangefileS:TrackchangefileService,private candidateFileExplorerService: CandidatefileexplorerService, private http: HttpClient,private codeFileService:CodefileService, private candidatedataService: CandidatedataService,private userIdService:UserIDService)
  {
    this.trackchangefile=trackchangefileS;
  }
  getFileIcon(fileName: string): string {
    const fileExtension = this.getFileExtension(fileName);
    // Add logic to map file extensions to corresponding icons
    switch (fileExtension.toLowerCase()) {
      case 'md':
        return 'description';
      case 'html':
        return 'code';
      case 'scss':
        return 'style';
      // case 'spec.ts':
      //     return 'library_books';
      case 'js':
        return 'language';
      case 'ts':
        return 'library_books';
        // case 'component.spec.ts':
        // return 'library_books';
      case 'json':
        return 'code';
      default:
        return 'insert_drive_file'; // Default icon for unknown file types
    }
  }
  ngOnInit(): void {
    this.userIdService.userId$.subscribe((userId) => {
      console.log("explorer"+userId);
        this.userId=userId;
        const userIdPart = this.userId ? `/${this.userId}` : '';
          this.apiUrl = `${this.url}${userIdPart}`;
          console.log("exploer "+this.apiUrl);
    });
    this.fetchProjectStructure();
    this.getProjectName();
  }
  getProjectName(): void {
    this.candidateFileExplorerService.getProjectName(this.userId).subscribe(
        (response: any) => {
            this.projectName = response;
            console.log('Project Name:', this.projectName);
        },
        (error) => {
            console.error('Error fetching project name:', error);
            console.log('Full error response:', error);
        }
    );
}
  fetchProjectStructure() {

    this.http.get<TreeNode[]>(this.apiUrl).subscribe(
      (data) => {
        console.log('API Response:', data);
        this.dataSource.data = this.buildTree(data);
      },
      (error) => {
        console.error('Error fetching project structure:', error);
      }
    );
  }
  buildTree(data: TreeNode[]): TreeNode[] {
    const tree: TreeNode[] = [];
    data.forEach((node) => {
      this.addNodeToTree(tree, node.name.split('/'), node.children);
    });
    console.log('Built Tree:', tree);
    return tree;
  }
  private addNodeToTree(tree: TreeNode[], pathParts: string[], children?: TreeNode[]): void {
    let currentLevel: TreeNode[] = tree;
    pathParts.forEach((part, index) => {
      const existingNode = currentLevel.find((n) => n.name === part);
      if (existingNode) {
        // If the node already exists, update the current level to its children
        currentLevel = existingNode.children || [];
      } else {
        // If the node doesn't exist, create a new node and add it to the current level
        const newNode: TreeNode = {
          name: part,
          children: index === pathParts.length - 1 ? children || [] : [],
          parent: null
        };
        currentLevel.push(newNode);
        // Update the current level to the new node's children
        currentLevel = newNode.children || [];
      }
    });
  }
  findParent(node: TreeNode): TreeNode | null {
    return node.parent ? node.parent : null;
  }
  isLeafNode(node: TreeNode): boolean {
    return !node.children || node.children.length === 0;
  }
  getFileExtension(filename: string): string {
    return filename.slice(((filename.lastIndexOf(".") - 1) >>> 0) + 2);
  }
  getLanguageForExtension(fileExtension: string): string {
    // Add logic to map file extensions to Monaco Editor language modes
    switch (fileExtension) {
      case 'md':
        return 'md';
      case 'html':
        return 'html';
      case 'scss':
        return 'css';
      case 'js':
        return 'javascript';
      case 'ts':
        return 'typescript';
      case 'json':
        return 'Json';
      default:
        return 'plaintext'; // Default to plaintext if no specific language is defined
    }
  }
  getNodePath(targetNode: TreeNode, nodes: TreeNode[]): string {
    const path: string[] = [];
    const findPath = (currentNode: TreeNode, currentPath: string[]) => {
      if (currentNode === targetNode) {
        path.push(...currentPath);
        return true;
      }
      if (currentNode.children) {
        for (const childNode of currentNode.children) {
          const newPath = [...currentPath, childNode.name];
          if (findPath(childNode, newPath)) {
            return true;
          }
        }
      }
      return false;
    };
    for (const rootNode of nodes) {
      if (findPath(rootNode, [rootNode.name])) {
        break;
      }
    }
    return path.join('/');
  }
  isEditableNode(node: TreeNode): boolean {
    // Customize this method based on your criteria to determine whether a node is editable
    const fileName = node.name.toLowerCase();
    return fileName.includes('component.ts') || fileName.includes('component.scss') || fileName.includes('component.html');
  }
  onNodeClick(node: TreeNode): void {


    const path = this.getNodePath(node, this.dataSource.data);
    const fileExtension = this.getFileExtension(node.name);
    const language = this.getLanguageForExtension(fileExtension);
    if (this.isLeafNode(node)) {
      if(this.previous==null){
        this.previous=node.name;
      }
      if(this.trackchangefile.trackmap.has(node.name) || this.previous!=null){
        console.log(node.name+" from click node component")
        if(!this.trackchangefile.trackmap.has(node.name)){
        this.trackchangefile.trackmap.append(this.previous,`${localStorage.getItem('monacoEditorContent')}`);
      //   this.trackchangefile.trackmap.forEach((value: FormDataEntryValue, key: string) => {
      //   console.log(`Key: ${key}, Value: ${value}`);
      // });
      this.previous=node.name;
    }
      else{
        this.trackchangefile.trackmap.delete(this.previous);
        this.trackchangefile.trackmap.append(this.previous,`${localStorage.getItem('monacoEditorContent')}`);
        this.trackchangefile.trackmap.forEach((value: FormDataEntryValue, key: string) => {
        console.log(`Key: ${key}, Value: ${value}`);
      });
      this.previous=node.name;
    }
  }
  this.candidatedataService.updateSelectedNodeName(node.name);
     
      this.candidateFileExplorerService.getFileContent(path,this.userId).pipe(
        // Use the map operator to transform the response to a string
        map((response: any) => {
          if (typeof response === 'object') {
            // Convert the object to a JSON string
            return JSON.stringify(response);
          } else {
            // If the response is already a string, return it as is
            return response;
          }
        })
      ).subscribe(
        (response) => {
          // console.log('File Content:', response);
          try {
            // Parse the response as JSON
            const parsedResponse = JSON.parse(response);
            // Check if parsedResponse is an object
            if (typeof parsedResponse === 'object') {
              // Find the first value in the object
              const fileContent = Object.values(parsedResponse)[0];
              // console.log(fileContent);
              // Check if the value is a string
              if (typeof fileContent === 'string') {
                this.codeFileService.setLanguage(language);
                this.codeFileService.setFileContent(this.trackchangefile.trackmap.has(this.previous)?this.trackchangefile.trackmap.get(this.previous):fileContent);
                
                // this.codeFileService.setFileContent(fileContent);
                // this.codeFileService.setLanguage(language);
                // Update the file content using the method in MonacoComponent
                // this.monacoEditor.setEditorContent(fileContent, language);
              } else {
                console.error('Invalid file content format:', fileContent);
              }
            } else {
              console.error('Invalid JSON format:', response);
            }
          } catch (error) {
            console.error('Error parsing JSON:', error);
          }
        },
        (error) => {
          console.error('Error fetching file content:', error);
        }
      );
    } else {
      console.log('Selected Node is not a file or does not have a filePath.');
    }
    console.log('Clicked Node Path:', path);
  }
  onNodeMouseEnter(node: TreeNode): void {
    node.hovered = true;
  }
  onNodeMouseLeave(node: TreeNode): void {
    node.hovered = false;
  }
}