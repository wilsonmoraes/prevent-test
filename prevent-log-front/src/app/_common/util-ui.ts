/* tslint:disable:member-ordering object-literal-shorthand no-string-literal max-line-length */
import {FormGroup} from '@angular/forms';
import {HttpResponse} from '@angular/common/http';

export class UtilUi {


  /**
   * Prepara uma lista para o dropdownlist
   */
  static forDropdown(descriptionProperty, valueProperty, list) {
    const dropdownList = [];
    list.forEach(item => {
      dropdownList.push({label: item[descriptionProperty], value: item[valueProperty]});
    });
    return dropdownList;
  }

  static forDropdownWithoutValueProperty(label, descriptionProperty, list) {
    const dropdownList = [{label: label, value: undefined}];
    list.forEach(item => {
      dropdownList.push({label: item[descriptionProperty], value: item});
    });
    return dropdownList;
  }


  /**
   * Prepara uma lista para o multiSelect
   */
  static forMultiSelect(descriptionProperty, list) {
    const multiSlectList = [];
    list.forEach(item => {
      multiSlectList.push({label: item[descriptionProperty], value: item});
    });
    return multiSlectList;
  }


  static getPageableRequest(event?): any {
    const sorting: { [key: string]: string } = {};
    const retorno: any = {};
    let page = 0;
    if (event && event.first > 0) {
      page = event.first / event.rows;
    }
    const size = event && event.rows > 0 ? event.rows : 10;

    if (event && event.sortField) {
      sorting[event.sortField] = event.sortOrder === -1 ? 'desc' : 'asc';
      retorno['sorting'] = sorting;
    }
    retorno['page'] = page;
    retorno['size'] = size;
    if (event && event['filters']) {
      for (const k in event['filters']) {
        if (event['filters'][k]) {
          retorno[k] = event['filters'][k]['value'];
        }
      }
    }
    return retorno;
  }


  static validarIntervaloDatas(dataInicio: any, dataFim: any) {
    if (UtilUi.stringToDate(dataInicio) <= UtilUi.stringToDate(dataFim)) {
      return true;
    } else {
      return false;
    }
  }

  static isNullOrEmpty(obj) {
    return obj === null || obj === undefined;
  }

  static isNotNullOrEmpty(obj) {
    return !UtilUi.isNullOrEmpty(obj);
  }

  static oneIsNullOrEmpty(obj: Array<any>) {
    for (const el of obj) {
      if (UtilUi.isNullOrEmpty(el)) {
        return true;
      }
    }
    return false;
  }


  static stringToDate(dateStr: string, hasTime?: boolean, isISO?: boolean) {
    let dateReturn: Date;
    if (dateStr && !isISO) {
      const [day, month, year] = dateStr.split('/');

      if (hasTime) {
        const hhmmss: string = dateStr.substr(dateStr.indexOf(' '));
        const [hh, mm, ss] = hhmmss.split(':');

        dateReturn = new Date(Number(year.substring(0, year.indexOf(' '))), Number(month) - 1, Number(day), Number(hh), Number(mm), Number(ss));
        console.log(dateReturn);
      } else {
        dateReturn = new Date(Number(year), Number(month) - 1, Number(day));
      }

    } else if (dateStr) {
      dateReturn = new Date(dateStr);
    }
    return dateReturn;
  }

  static dateToString(dateParam: any, useTime?: boolean) {
    if (UtilUi.isNullOrEmpty(dateParam)) {
      return null;
    }
    let dd = dateParam.getDate();
    let mm = dateParam.getMonth() + 1;

    const yyyy = dateParam.getFullYear();

    if (dd < 10) {
      dd = '0' + dd;
    }
    if (mm < 10) {
      mm = '0' + mm;
    }

    return `${dd}/${mm}/${yyyy} ${useTime ?
      String(dateParam.getHours()).padStart(2, '0') + ':' + String(dateParam.getMinutes()).padStart(2, '0') + ':' + String(dateParam.getSeconds()).padStart(2, '0') : ''}`.trim();
  }


  static intervaloDataInvalido(campoA: string, campoB: string) {
    return (group: FormGroup): { [key: string]: boolean } => {
      const dataInicio = group.controls[campoA]['value'];
      const dataFim = group.controls[campoB]['value'];
      if (!dataFim && !dataInicio) {
        return null;
      } else if (!dataFim && dataInicio || dataFim && !dataInicio) {
      } else if (UtilUi.stringToDate(dataInicio) > UtilUi.stringToDate(dataFim)) {
        return {intervaloDataInvalido: true};
      }
    };
  }

  static camposDiferentes(campoA: string, campoB: string) {
    return (group: FormGroup): { [key: string]: boolean } => {
      const email = group.controls[campoA];
      const confirmEmail = group.controls[campoB];

      if (email.value !== confirmEmail.value) {
        return {
          camposDiferentes: true
        };
      }
    };
  }

  public static formatSize(bytes) {
    if (bytes < 1024) {
      return bytes + ' Bytes';
    } else if (bytes < 1048576) {
      return (bytes / 1024).toFixed(3) + ' KB';
    } else if (bytes < 1073741824) {
      return (bytes / 1048576).toFixed(3) + ' MB';
    } else {
      return (bytes / 1073741824).toFixed(3) + ' GB';
    }
  }


  public static getIdFromHeader(httpResponse: HttpResponse<any>): number {
    let id = null;
    if (httpResponse.headers.get('location')) {
      const url = httpResponse.headers.get('location');
      id = url.substr(url.lastIndexOf('/') + 1);
    }
    return this.isNumber(id) ? Number(id) : null;
  }

  public static isNumber(value: string | number): boolean {
    return ((value != null) && !isNaN(Number(value.toString())));
  }

  public static defaultLocaleCalendar: any = {
    // date
    closeText: 'Fechar',
    prevText: '<Anterior',
    nextText: 'Próximo>',
    currentText: 'Hoy',
    monthNames: ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto',
      'Setembro', 'Outubro', 'Novembro', 'Dezembro'],
    monthNamesShort: ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'],
    dayNames: ['Domingo', 'Segunda', 'Terça', 'Quarta', 'Quinta', 'Sexta', 'Sábado'],
    dayNamesShort: ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sáb', 'Dom'],
    dayNamesMin: ['D', 'S', 'T', 'Q', 'Q', 'S', 'S', 'D'],
    weekHeader: 'Sm',
    dateFormat: 'dd/mm/yy',
    firstDay: 1,
    isRTL: false,
    showMonthAfterYear: false,
    yearSuffix: '',
    // time
    timeOnlyTitle: 'Escolher uma  hora',
    timeText: 'Hora',
    hourText: 'Horas',
    minuteText: 'Minutos',
    secondText: 'Segundos',
    millisecText: 'Milisegundos',
    microsecText: 'Microsegundos',
    timezoneText: 'Fuso horário',
    timeFormat: 'HH:mm',
    timeSuffix: '',
    amNames: ['a.m.', 'AM', 'A'],
    pmNames: ['p.m.', 'PM', 'P'],
  };

  public static estadosByNome: { item: string, estado_id: number, preco?: number }[] = [
    {item: 'Acre', estado_id: 12}, {item: 'Alagoas', estado_id: 27},
    {item: 'Amapá', estado_id: 16}, {item: 'Amazonas', estado_id: 13},
    {item: 'Bahia', estado_id: 29}, {item: 'Ceará', estado_id: 23},
    {item: 'Distrito Federal', estado_id: 53}, {item: 'Espírito Santo', estado_id: 32},
    {item: 'Goiás', estado_id: 52}, {item: 'Maranhão', estado_id: 21},
    {item: 'Mato Grosso', estado_id: 51}, {item: 'Mato Grosso do Sul', estado_id: 50},
    {item: 'Minas Gerais', estado_id: 31}, {item: 'Paraná', estado_id: 41},
    {item: 'Paraíba', estado_id: 25}, {item: 'Pará', estado_id: 15},
    {item: 'Pernambuco', estado_id: 26}, {item: 'Piauí', estado_id: 22},
    {item: 'Rio Grande do Norte', estado_id: 24}, {item: 'Rio Grande do Sul', estado_id: 43},
    {item: 'Rio de Janeiro', estado_id: 33}, {item: 'Rondônia', estado_id: 11},
    {item: 'Roraima', estado_id: 14}, {item: 'Santa Catarina', estado_id: 42},
    {item: 'Sergipe', estado_id: 28}, {item: 'São Paulo', estado_id: 35},
    {item: 'Tocantins', estado_id: 17}
  ];

  public static truncateNumber(x, n) {
    const v = (typeof x === 'string' ? x : x.toString()).split('.');
    if (n <= 0) {
      return v[0];
    }
    let f = v[1] || '';
    if (f.length > n) {
      return `${v[0]}.${f.substr(0, n)}`;
    }
    while (f.length < n) {
      f += '0';
    }
    return `${v[0]}.${f}`;
  }

}
