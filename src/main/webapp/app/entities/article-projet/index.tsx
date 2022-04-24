import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ArticleProjet from './article-projet';
import ArticleProjetDetail from './article-projet-detail';
import ArticleProjetUpdate from './article-projet-update';
import ArticleProjetDeleteDialog from './article-projet-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ArticleProjetUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ArticleProjetUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ArticleProjetDetail} />
      <ErrorBoundaryRoute path={match.url} component={ArticleProjet} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ArticleProjetDeleteDialog} />
  </>
);

export default Routes;
