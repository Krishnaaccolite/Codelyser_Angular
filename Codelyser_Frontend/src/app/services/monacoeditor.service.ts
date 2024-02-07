import { Injectable } from '@angular/core';
declare const monaco: any;
@Injectable({
  providedIn: 'root'
})
export class MonacoeditorService {
  private monacoScriptLoaded = false;

  initMonaco(): Promise<void> {
    return new Promise<void>((resolve) => {
      if (typeof monaco !== 'undefined') {
        // Monaco is already loaded, resolve immediately
        resolve();
      } else {
        // Load Monaco script dynamically
        const script = document.createElement('script');
        script.src = 'https://unpkg.com/monaco-editor@latest/min/vs/loader.js';
        script.onload = () => {
          // Configure Monaco after loader script is loaded
          (window as any).require.config({ paths: { vs: 'https://unpkg.com/monaco-editor@latest/min/vs' } });
          
          // Load the necessary modules for the editor
          (window as any).require(['vs/editor/editor.main'], () => {
            // Mark Monaco as loaded
            this.monacoScriptLoaded = true;
            resolve();
          });
        };

        // Append the script to the document body
        document.body.appendChild(script);
      }
    });
  }
}
