import {
  FormGroup,
  FormBuilder,
  Validators,
  ReactiveFormsModule,
} from '@angular/forms';
import { DadosService } from './dados.service';
import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-atividadejunta',
  standalone: true,
  imports: [
    CommonModule,
    MatButtonModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    MatInputModule,
    MatButtonModule,
  ],
  templateUrl: './atividadejunta.component.html',
  styleUrl: './atividadejunta.component.scss',
})
export class AtividadejuntaComponent {
  items: any[] = [];
  userForm!: FormGroup;
  id = '';

  constructor(
    private readonly dadosService: DadosService,
    private readonly formBuilder: FormBuilder,
    private readonly route: ActivatedRoute
  ) {
    this.items = this.dadosService.getData();
    this.userForm = this.formBuilder.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      number: ['', [Validators.required, Validators.pattern('^[0-9]*$')]],
    });

    this.route.params.subscribe((params) => (this.id = params['id']));
  }

  boleano = false;

  dataSaved: string = '';
  botaoText: string = 'Clique no botão';

  onInput(event: any) {
    this.dataSaved = event.target.value;
  }

  OnSubmit() {
    this.dataSaved = this.userForm.value.name + ' ' + this.userForm.value.email;
  }

  onBotaoClicado() {
    this.botaoText = 'Botão clicado';
    this.boleano = !this.boleano;
  }
}
