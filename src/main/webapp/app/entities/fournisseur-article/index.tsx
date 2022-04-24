import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import FournisseurArticle from './fournisseur-article';
import FournisseurArticleDetail from './fournisseur-article-detail';
import FournisseurArticleUpdate from './fournisseur-article-update';
import FournisseurArticleDeleteDialog from './fournisseur-article-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={FournisseurArticleUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={FournisseurArticleUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={FournisseurArticleDetail} />
      <ErrorBoundaryRoute path={match.url} component={FournisseurArticle} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={FournisseurArticleDeleteDialog} />
  </>
);

export default Routes;
