import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Frais from './frais';
import FraisDetail from './frais-detail';
import FraisUpdate from './frais-update';
import FraisDeleteDialog from './frais-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={FraisUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={FraisUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={FraisDetail} />
      <ErrorBoundaryRoute path={match.url} component={Frais} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={FraisDeleteDialog} />
  </>
);

export default Routes;
