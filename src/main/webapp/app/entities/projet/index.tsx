import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Projet from './projet';
import ProjetDetail from './projet-detail';
import ProjetUpdate from './projet-update';
import ProjetDeleteDialog from './projet-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ProjetUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ProjetUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ProjetDetail} />
      <ErrorBoundaryRoute path={match.url} component={Projet} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ProjetDeleteDialog} />
  </>
);

export default Routes;
