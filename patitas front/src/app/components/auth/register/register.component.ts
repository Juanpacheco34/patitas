import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { AuthService } from '../../../services/auth/auth.service';
import { CloudinaryService } from '../../../services/cloudinary/cloudinary.service';
import { PatitaUser } from '../../../interfaces/patita-user';

@Component({
  selector: 'app-register',
  imports: [ReactiveFormsModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class RegisterComponent implements OnInit {
  image_perfil: string = 'assets/img/load-image.svg';
  file: File | undefined;
  registerForm: FormGroup = new FormGroup({});

  constructor(
    private fb: FormBuilder,
    private auth: AuthService,
    private cloudinary: CloudinaryService
  ) {}
  ngOnInit(): void {
    this.registerForm = this.fb.group({
      name: ['', Validators.compose([Validators.required])],
      lastname: ['', Validators.compose([Validators.required])],
      email: ['', Validators.compose([Validators.email, Validators.required])],
      password: ['', Validators.compose([Validators.required])],
      repeatPassword: ['', Validators.compose([Validators.required])],
      dni: ['', Validators.compose([Validators.required])],
      description: [''],
      web: [''],
      picture: [''],
      termsAndConditions: [false, Validators.requiredTrue],
      rol: [{ id: 1 }],
    });
  }

  get name() {
    return this.registerForm.get('name');
  }

  get lastname() {
    return this.registerForm.get('lastname');
  }

  get email() {
    return this.registerForm.get('email');
  }

  get password() {
    return this.registerForm.get('password');
  }
  get repeatPassword() {
    return this.registerForm.get('repeatPassword');
  }

  get dni() {
    return this.registerForm.get('dni');
  }

  get description() {
    return this.registerForm.get('description');
  }

  get web() {
    return this.registerForm.get('web');
  }

  get termsAndConditions() {
    return this.registerForm.get('termsAndConditions');
  }
  get picture() {
    return this.registerForm.get('picture');
  }

  get rol() {
    return this.registerForm.get('rol');
  }

  /*Cuando se suba el archivo se convierte en base64 para
  luego enviarlo a la etiqueta image y cargar la image*/
  loadedFile(event: Event): void {
    this.file = (event.target as HTMLInputElement).files?.[0];
    if (this.file && this.file.type.startsWith('image/')) {
      const reader = new FileReader();
      // actualizamos la imagen
      reader.onload = () => {
        this.image_perfil = reader.result as string;
      };
      reader.readAsDataURL(this.file);
    } else {
      alert('Insertar imagen de formato valido');
    }
  }

  sendForm(registerForm: FormGroup): void {
    const register: PatitaUser = registerForm.value;

    if (this.file && registerForm.valid) {
      this.cloudinary.uploadImage(this.file).subscribe({
        next: (res) => {
          if (res.secure_url) {
            registerForm.get('picture')?.setValue(res.secure_url);

            // this.auth.register(register).subscribe({
            //   next: (res) => { },

            //   error: (err) => {},
            // });
          }
        },
        error: (err) =>
          console.log('Error al cargar la imagen', err.error.message),
      });
    } else if (registerForm.valid && !this.file) {
      this.auth.register(register).subscribe({
        next: (res) => {
          console.log('Exito en el registro', res);
        },
        error: (err) => {},
      });
    } else {
      alert('Rellena el formulario correctamente');
    }
  }
}
