import { Moment } from 'moment';

export interface IPageUrl {
  id?: number;
  isbn?: string;
  page?: number;
  hash?: string;
  startTime?: Moment;
}

export class PageUrl implements IPageUrl {
  constructor(public id?: number, public isbn?: string, public page?: number, public hash?: string, public startTime?: Moment) {}
}
