import { Component,ViewChild,ElementRef, OnInit, OnDestroy,Output,EventEmitter,NgModule} from '@angular/core';
import { MonacoeditorService } from '../services/monacoeditor.service';
import { Subscription } from 'rxjs';
import { CodefileService } from '../services/codefile.service';
import { CompilecodeService } from '../services/compilecode.service';
import { CandidatedataService } from '../services/candidatedata.service';
import { TestcaseService } from '../services/testcase.service';
// import { SavefileService } from '../services/savefile.service';
declare const monaco: any;
@Component({
  selector: 'app-monaco-editor',
  templateUrl: './monaco-editor.component.html',
  styleUrl: './monaco-editor.component.scss'
})
export class MonacoEditorComponent implements OnInit,OnDestroy {
  isDarkTheme: boolean = true;
  selectedNodeName: string = '';
  private fileContentSubscription: Subscription | undefined;
  compilecode!:string;
@ViewChild('editorContainer') editorContainer!: ElementRef;
@Output() runCodeClicked = new EventEmitter<{ livecode: string}>();
  private editor: any;
  isFullScreen: any;
  constructor(private monacoService:MonacoeditorService,private codeFileService:CodefileService,private compileCodeService:CompilecodeService, private candidatedataService: CandidatedataService,private testCaseService:TestcaseService)
  {
  }
  ngOnInit() {
    this.candidatedataService.selectedNodeName$.subscribe((name) => {
      this.selectedNodeName = name;
    });
    this.monacoService.initMonaco().then(() => {
      localStorage.clear();
      this.initializeEditor();
      this.editor.onDidChangeModelContent(() => {
        this.onEditorContentChange();
      });
    });
    this.fileContentSubscription = this.codeFileService.currentFileContent.subscribe((content) => {
      this.codeFileService.isRunButtonClicked.subscribe((isClicked) => {
        if (!isClicked) {
          // Update the editor content when the file content changes
          this.codeFileService.currentLanguage.subscribe((language) => {
            this.setEditorContent(content, language);
          });
        }
      });
    });
  }
  ngOnDestroy(): void {
    this.fileContentSubscription?.unsubscribe();
  }
  initializeEditor() {
    if (this.editor) {
      this.editor.dispose();
    }
    this.editor = monaco.editor.create(this.editorContainer.nativeElement, {
      theme: 'vs-dark',
      automaticLayout: true,
    });
  }

  onEditorContentChange() {
    // This method is called whenever the content in the editor change
  const editorContent = this.editor.getValue();
  localStorage.setItem('monacoEditorContent', editorContent);
}

  setEditorContent(content: string, language: string) {
    if (this.editor) {
      // Check if the file name contains "component.ts," "component.scss," or "component.html"
      const fileName = this.selectedNodeName.toLowerCase();
      const isEditableFile = fileName.includes('component.ts') || fileName.includes('component.scss') || fileName.includes('component.html');
      if (isEditableFile) {
        // Allow editing for files with names containing "component.ts," "component.scss," or "component.html"
        console.log("edi");
        this.editor.updateOptions({ readOnly: false });
      } else {
        // Make other files read-only
        this.editor.updateOptions({ readOnly: true });
      }
      // if (fileName.includes('spec.ts')) {
      //   this.editor.setValue('');
        // Set editor content for other files
        this.editor.setValue(content);
        this.editor.getModel()?.updateOptions({ language });
        monaco.editor.setModelLanguage(this.editor.getModel(), language);

    }
    console.log('Editor content set:', content);
  }
  runCode():void
  {
    const code = this.editor.getValue();
    const filename=this.selectedNodeName;
    console.log("From Monaco"+ filename);
    this.compileCodeService.triggerRunCode(code,filename);
  }
  selectedTheme: string = "vs-dark";
  toggleTheme(): void {
    this.selectedTheme = this.selectedTheme === 'vs' ? 'vs-dark' : 'vs';
    this.isDarkTheme = this.selectedTheme === 'vs-dark';
    if (this.editor) {
      this.editor.updateOptions({ theme: this.selectedTheme });
    }
  }
  runTests():void
  {
    console.log("test cases");
    this.testCaseService.triggerMethod();
  }
  toggleFullScreen() {
    const container = this.editorContainer.nativeElement;
    if (!document.fullscreenElement) {
      const requestFullscreen =
        container.requestFullscreen ||
        (container as any).mozRequestFullScreen ||
        (container as any).webkitRequestFullscreen ||
        (container as any).msRequestFullscreen;
      if (requestFullscreen) {
        requestFullscreen.call(container).then(() => {
          this.isFullScreen = true;
          this.adjustEditorSize();
          // this.setEditorContainerStyles(true); // Set styles for fullscreen
        }).catch((error: any) => {
          console.error("Failed to enter fullscreen:", error);
        });
      }
    } else {
      const exitFullscreen =
        document.exitFullscreen ||
        (document as any).mozCancelFullScreen ||
        (document as any).webkitExitFullscreen ||
        (document as any).msExitFullscreen;
      if (exitFullscreen) {
        exitFullscreen.call(document).then(() => {
          this.isFullScreen = false;
          this.adjustEditorSize();
          // this.setEditorContainerStyles(false); // Reset styles after exiting fullscreen
        }).catch((error) => {
          console.error("Failed to exit fullscreen:", error);
        });
      }
    }
  }
  adjustEditorSize() {
    setTimeout(() => {
      const monacoEditor = monaco.editor.getModels()[0];
      if (monacoEditor) {
        monacoEditor.layout();
      }
    }, 100);
  }
  clearEditor(): void {
    if (this.editor) {
      this.editor.setValue('');
      this.selectedNodeName = '';
    }
  }
}