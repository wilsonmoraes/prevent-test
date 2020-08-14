/* tslint:disable:no-string-literal */
import {Component, OnInit} from '@angular/core';
import {PostService} from '../post.service';
import {ActivatedRoute, Router} from '@angular/router';
import {FormBuilder, FormGroup} from '@angular/forms';
import {AccessLogService} from '../access-log.service';
import {UtilUi} from '../../_common/util-ui';

@Component({
  selector: 'app-create',
  templateUrl: './create.component.html',
  styleUrls: ['./create.component.css']
})
export class CreateComponent implements OnInit {

  form: FormGroup;
  public file: File;
  public id;
  public defaultLocaleCalendar = UtilUi.defaultLocaleCalendar;


  constructor(
    public postService: PostService,
    private router: Router,
    private accessLogService: AccessLogService,
    private route: ActivatedRoute,
    private formBuilder: FormBuilder
  ) {
    this.form = this.formBuilder.group({
      client: [null],
      clientIP: [null],
      methodRequest: [null],
      statusCodeResponde: [null],
      dateTimeAudit: [null]
    });
  }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['postId'];
    if (this.id) {
      this.findByApi();
    }
  }

  get f() {
    return this.form.controls;
  }

  submit() {
    const value = this.form.value;
    const obj = {
      id: this.id,
      client: value['client'],
      clientIP: value['clientIP'],
      methodRequest: value['methodRequest'],
      statusCodeResponde: value['statusCodeResponde'],
      dateTimeAuditAux: UtilUi.dateToString(value['dateTimeAudit'], true)
    };
    console.log(obj);
    this.accessLogService.post(obj, '').subscribe(() => {
      console.log('enviado');
      this.router.navigate([`post/index`]);
    });
  }

  onFileSelect(event: Event) {
    if (event.target['files'][0]) {
      this.file = event.target['files'][0];

    }
  }

  enviarArquivo() {
    this.accessLogService.uploadLog(this.file).subscribe(() => {
      console.log('arquivo enviado com sucesso');
      this.router.navigate([`post/index`]);
      // /post/index
    });
  }

  private findByApi() {
    this.accessLogService.findById(this.id).subscribe((res) => {
      this.form.patchValue({client: res['client']});
      this.form.patchValue({clientIP: res['clientIP']});
      this.form.patchValue({methodRequest: res['methodRequest']});
      this.form.patchValue({client: res['client']});
      this.form.patchValue({statusCodeResponde: res['statusCodeResponde']});
      this.form.patchValue({dateTimeAudit: UtilUi.stringToDate(res['dateTimeAudit'], true)});
    });
  }
}
