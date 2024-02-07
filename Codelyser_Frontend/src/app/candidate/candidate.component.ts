import { Component, ElementRef, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
@Component({
  selector: 'app-candidate',
  templateUrl: './candidate.component.html',
  styleUrl: './candidate.component.scss'
})
export class CandidateComponent {
  userId: string | null = null;
  
  constructor(private route: ActivatedRoute) {}

  ngOnInit(): void {
   document.documentElement.requestFullscreen(); 
    this.route.paramMap.subscribe((params: { get: (arg0: string) => string | null; }) => {
      this.userId = params.get('userId');
      console.log('User ID:', this.userId);
    });
  }


}




// constructor(private route: ActivatedRoute) {}

//   ngOnInit(): void {
//     this.route.paramMap.subscribe((params: { get: (arg0: string) => string | null; }) => {
//       this.userId = params.get('userId');
//       console.log('User ID:', this.userId);

//       // Trigger fullscreen when user logs in
//       this.requestFullscreen();
//     });
//   }

//   requestFullscreen(): void {
//     const element = document.documentElement;

//     if (element.requestFullscreen) {
//       element.requestFullscreen();
//     } else if ((element as any).mozRequestFullScreen) {
//       (element as any).mozRequestFullScreen();
//     } else if ((element as any).webkitRequestFullscreen) {
//       (element as any).webkitRequestFullscreen();
//     } else if ((element as any).msRequestFullscreen) {
//       (element as any).msRequestFullscreen();
//     }
//   }
// }