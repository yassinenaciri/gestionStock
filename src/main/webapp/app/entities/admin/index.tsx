import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Admin from './admin';
import AdminDetail from './admin-detail';
import AdminUpdate from './admin-update';
import AdminDeleteDialog from './admin-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AdminUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AdminUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AdminDetail} />
      <ErrorBoundaryRoute path={match.url} component={Admin} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={AdminDeleteDialog} />
  </>
);

export default Routes;
