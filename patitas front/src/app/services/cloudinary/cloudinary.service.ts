import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CloudinaryService {
  private readonly CLOUD_NAME: string = 'ddo3iuibt';
  private readonly UPLOAD_PRESET: string = 'Juanpacheco34';

  constructor(private hc: HttpClient) {}

  uploadImage(file: File): Observable<any> {
    const formData = new FormData();
    formData.append('file', file);
    formData.append('upload_preset', this.UPLOAD_PRESET);

    const url = `https://api.cloudinary.com/v1_1/${this.CLOUD_NAME}/image/upload`;

    return this.hc.post(url, formData);
  }
}
