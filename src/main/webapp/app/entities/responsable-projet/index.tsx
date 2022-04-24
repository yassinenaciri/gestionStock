import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ResponsableProjet from './responsable-projet';
import ResponsableProjetDetail from './responsable-projet-detail';
import ResponsableProjetUpdate from './responsable-projet-update';
import ResponsableProjetDeleteDialog from './responsable-projet-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ResponsableProjetUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ResponsableProjetUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ResponsableProjetDetail} />
      <ErrorBoundaryRoute path={match.url} component={ResponsableProjet} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ResponsableProjetDeleteDialog} />
  </>
);

export default Routes;
