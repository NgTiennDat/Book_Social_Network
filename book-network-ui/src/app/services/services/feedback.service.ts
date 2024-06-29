/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { addFeedback } from '../fn/feedback/add-feedback';
import { AddFeedback$Params } from '../fn/feedback/add-feedback';
import { allFeedBackByBook } from '../fn/feedback/all-feed-back-by-book';
import { AllFeedBackByBook$Params } from '../fn/feedback/all-feed-back-by-book';
import { PageResponseFeedbackResponse } from '../models/page-response-feedback-response';

@Injectable({ providedIn: 'root' })
export class FeedbackService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `addFeedback()` */
  static readonly AddFeedbackPath = '/feedbacks';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `addFeedback()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  addFeedback$Response(params: AddFeedback$Params, context?: HttpContext): Observable<StrictHttpResponse<number>> {
    return addFeedback(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `addFeedback$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  addFeedback(params: AddFeedback$Params, context?: HttpContext): Observable<number> {
    return this.addFeedback$Response(params, context).pipe(
      map((r: StrictHttpResponse<number>): number => r.body)
    );
  }

  /** Path part for operation `allFeedBackByBook()` */
  static readonly AllFeedBackByBookPath = '/feedbacks/book/{book-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `allFeedBackByBook()` instead.
   *
   * This method doesn't expect any request body.
   */
  allFeedBackByBook$Response(params: AllFeedBackByBook$Params, context?: HttpContext): Observable<StrictHttpResponse<PageResponseFeedbackResponse>> {
    return allFeedBackByBook(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `allFeedBackByBook$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  allFeedBackByBook(params: AllFeedBackByBook$Params, context?: HttpContext): Observable<PageResponseFeedbackResponse> {
    return this.allFeedBackByBook$Response(params, context).pipe(
      map((r: StrictHttpResponse<PageResponseFeedbackResponse>): PageResponseFeedbackResponse => r.body)
    );
  }

}
