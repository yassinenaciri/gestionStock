import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import SousAdmin from './sous-admin';
import SousAdminDetail from './sous-admin-detail';
import SousAdminUpdate from './sous-admin-update';
import SousAdminDeleteDialog from './sous-admin-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SousAdminUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SousAdminUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SousAdminDetail} />
      <ErrorBoundaryRoute path={match.url} component={SousAdmin} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SousAdminDeleteDialog} />
  </>
);

export default Routes;
