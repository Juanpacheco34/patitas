import { ChangeDetectionStrategy, Component } from '@angular/core';
import { SidebarComponent } from '../../components/sidebar/sidebar.component';
import { CreatePostComponent } from '../../components/create-post/create-post.component';

@Component({
  selector: 'app-dashboard',
  imports: [SidebarComponent,CreatePostComponent],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class DashboardComponent {

}
