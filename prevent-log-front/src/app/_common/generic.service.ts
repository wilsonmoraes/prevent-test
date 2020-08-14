/* tslint:disable:variable-name ban-types no-string-literal */
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';

import {environment} from '../../environments/environment';
import {UtilUi} from './util-ui';

/**
 * Prepara um objeto para uma pesquisa get com parâmetros
 */
export const objToHttpParams = (obj: any): HttpParams => {
  let params = new HttpParams();
  for (const k in obj) {
    if (obj[k] !== null) {
      params = params.append(k, obj[k]);
    }
  }
  return params;
};

/**
 * Result para uma consulta paginada
 */
export class PageResult {
  public totalElements = 0;
  public page = 0;
  public size = 10;
  public content: Array<any> = [];
}


export abstract class GenericService {


  protected constructor(private http: HttpClient,
                        protected baseUrl: string) {
    this.baseUrl = `${environment.apiUrl}/${baseUrl}`;
  }


  /**
   * post
   */
  public post(param: Object, url: string = '', getHttpResponse: Boolean = false): Observable<any> {
    if (getHttpResponse) {
      return this.http.post(`${this.baseUrl}/${url}`, param, {observe: 'response'});
    }
    return this.http.post(`${this.baseUrl}/${url}`, param);
  }


  /**
   * get
   */
  public get(config: { url?: string, params?: Object } = {url: '', params: undefined}): Observable<any> {
    if (config.params) {
      return this.http.get(`${this.baseUrl}/${config.url}`, {params: objToHttpParams(config.params)});
    } else {
      return this.http.get(`${this.baseUrl}/${config.url}`);
    }
  }


  /**
   * delete
   */
  public delete(url: string): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${url}`);
  }


  /**
   * put
   */
  public put(config: { url?: string, params?: Object, body?: Object } = {
    url: '',
    params: undefined,
    body: undefined
  }): Observable<any> {
    if (config.params) {
      return this.http.put(`${this.baseUrl}/${config.url}`, config.body, {params: objToHttpParams(config.params)});
    } else {
      return this.http.put(`${this.baseUrl}/${config.url}`, config.body);
    }
  }

  postWithFile(url: string, postData: any, multiPartFile: { param: string, file: File }[]) {
    const formData = this.convertToFormData(multiPartFile, postData);
    return this.http.post(`${this.baseUrl}/${url}`, formData);
  }

  putWithFile(url: string, postData: any, multiPartFile: { param: string, file: File }[]) {
    const formData = this.convertToFormData(multiPartFile, postData);
    return this.http.put(`${this.baseUrl}/${url}`, formData);
  }


  private convertToFormData(multiPartFile: { param: string; file: File }[], postData: any) {
    const formData: FormData = new FormData();
    if (multiPartFile) {
      multiPartFile.forEach((item) => {
        if (item.file) {
          formData.append(item.param, item.file, item.file ? item.file.name : null);
        }
      });

    }

    if (postData) {
      for (const property in postData) {
        if (postData.hasOwnProperty(property)) {
          formData.append(property, JSON.stringify(postData[property]));
        }
      }

    }
    return formData;
  }

  public findAllPaged(method: string, tableEvent?: any, filtros?: any): Observable<any> {
    const search = UtilUi.getPageableRequest(tableEvent);
    delete search['filters'];
    for (const k in filtros) {
      if (filtros.hasOwnProperty(k)) {
        search[k] = filtros[k];
      }
    }
    return this.post(search, method);

  }


}
