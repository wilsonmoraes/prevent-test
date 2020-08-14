import {Injectable} from '@angular/core';

import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {GenericService} from '../_common/generic.service';


@Injectable()
export class AccessLogService extends GenericService {


  constructor(http: HttpClient) {
    super(http, 'open/access_log');
  }


  public uploadLog(log: File): Observable<any> {
    return this.postWithFile(`upload`, null, [{param: 'file', file: log}]);
  }

  public findAllPaginated(tableEvent?: any, filtros?: any) {
    if (!tableEvent) {
      tableEvent = {first: 0, rows: Number.MAX_SAFE_INTEGER};
    }
    return this.findAllPaged(`/paginated/`, tableEvent, filtros);
  }

  public findById(id: number) {
    return this.get({url: `${id}/`});
  }

}
