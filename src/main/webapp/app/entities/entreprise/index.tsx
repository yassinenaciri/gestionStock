import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Entreprise from './entreprise';
import EntrepriseDetail from './entreprise-detail';
import EntrepriseUpdate from './entreprise-update';
import EntrepriseDeleteDialog from './entreprise-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EntrepriseUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EntrepriseUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EntrepriseDetail} />
      <ErrorBoundaryRoute path={match.url} component={Entreprise} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={EntrepriseDeleteDialog} />
  </>
);

export default Routes;
