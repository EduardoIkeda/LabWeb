import { Injectable } from '@angular/core';
import { UntypedFormArray, UntypedFormControl, UntypedFormGroup } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class FormUtilsService {

  constructor() { }

  validateAllFormFields(formGroup: UntypedFormGroup | UntypedFormArray) {
    Object.keys(formGroup.controls).forEach(field => {
      const control = formGroup.get(field);

      if (control instanceof UntypedFormControl) {

        control.markAsTouched({ onlySelf: true });

      } else if (control instanceof UntypedFormGroup || control instanceof UntypedFormArray) {

        control.markAsTouched({ onlySelf: true });
        this.validateAllFormFields(control);

      }
    });
  }

  isFormArrayRequired(formGroup: UntypedFormGroup, formArrayName: string) {
    const formArray = formGroup.get(formArrayName) as UntypedFormArray;
    return !formArray.valid && formArray.hasError('required') && formArray.touched;
  }

  getErrorMessage(formGroup: UntypedFormGroup, fieldName: string) {
    const field = formGroup.get(fieldName) as UntypedFormControl;
    return this.getFieldErrorMessage(field);
  }

  getFormArrayFieldErrorMessage(formGroup: UntypedFormGroup, formArrayName: string, index: number, fieldName: string) {
    const formArray = formGroup.get(formArrayName) as UntypedFormArray;
    const field = formArray.controls[index].get(fieldName) as UntypedFormControl;
    return this.getFieldErrorMessage(field);
  }

  getFieldErrorMessage(field: UntypedFormControl) {
    if (field?.hasError('required')) {
      return 'Campo obrigatório';
    }
    if (field?.hasError('minlength')) {
      const requiredLength: number = field.errors
        ? field.errors['minlength']['requiredLength']
        : 8;
      return `Tamanho mínimo precisa ser de ${requiredLength} caracteres.`;
    }
    if (field?.hasError('maxlength')) {
      const requiredLength: number = field.errors
        ? field.errors['maxlength']['requiredLength']
        : 15;
      return `Tamanho máximo excedido de ${requiredLength} caracteres.`;
    }
    if (field?.hasError('pattern')) {
      return 'Digite apenas números';
    }
    if (field?.hasError('email')) {
      return 'Email inválido';
    }
    if (field?.hasError('notSame')) {
      return 'Senhas diferentes';
    }
    return 'Campo inválido';
  }
}
