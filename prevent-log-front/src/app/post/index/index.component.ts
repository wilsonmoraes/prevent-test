/* tslint:disable:no-string-literal */
import {Component, OnInit, ViewChild} from '@angular/core';
import {PostService} from '../post.service';
import {Post} from '../post';
import {AccessLogService} from '../access-log.service';
import {PageResult} from '../../_common/generic.service';
import {Router} from '@angular/router';
import {Table} from 'primeng';

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.css']
})
export class IndexComponent implements OnInit {

  posts: Post[] = [];
  pontoDistribuicaoResponse: PageResult;
  @ViewChild(Table) table: Table;


  cols: any[] = [
    {
      field: 'clientIP',
      sortField: 'clientIP',
      filterField: 'clientIP',
      header: 'IP',
      ngStyle: {width: '16%', 'word-wrap': 'break-word'}
    },
    {
      field: 'methodRequest',
      sortField: 'methodRequest',
      filterField: 'methodRequest',
      header: 'Method',
      ngStyle: {width: '16%', 'word-wrap': 'break-word'}
    }, {
      field: 'dateTimeAudit',
      sortField: 'dateTimeAudit',
      header: 'Date',
      ngStyle: {width: '16%', 'word-wrap': 'break-word'}
    }, {
      field: 'statusCodeResponde',
      sortField: 'statusCodeResponde',
      filterField: 'statusCodeResponde',
      header: 'Status',
      ngStyle: {width: '10%', 'word-wrap': 'break-word'}
    },
    {
      field: 'client',
      sortField: 'client',
      filterField: 'client',
      header: 'Client',
      ngStyle: {width: '26%', 'word-wrap': 'break-word'}
    },
    {
      field: 'action',
      header: 'Ações',
      ngStyle: {width: '100px', 'text-align': 'center', 'word-wrap': 'break-word'}
    }
  ];

  constructor(public postService: PostService,
              public accessLogService: AccessLogService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.postService.getAll().subscribe((data: Post[]) => {
      this.posts = data;
      console.log(this.posts);
    });
  }

  deletePost(id) {
    this.postService.delete(id).subscribe(res => {
      this.posts = this.posts.filter(item => item.id !== id);
      console.log('Post deleted successfully!');
    });
  }

  public findAllPaged(tableEvent: any, filtros: any): void {
    setTimeout(() => {
      this.accessLogService.findAllPaginated(tableEvent, filtros).subscribe((res) => {
        this.pontoDistribuicaoResponse = res;
      });
    });
  }

  remover(event: any) {
    this.accessLogService.delete(`${event['id']}`).subscribe(() => {
      const index = this.pontoDistribuicaoResponse['content'].findIndex(item => item.id === event['id']);
      if (index > -1) {
        this.pontoDistribuicaoResponse['content'].splice(index, 1);
      }
      console.log('Item removido');
    });
// this.table.reset();
  }

  visualizar(event: any) {
    this.router.navigate([`post/${event['id']}/view`]);
  }
}
