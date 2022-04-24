import React from 'react';
import { Switch } from 'react-router-dom';
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Admin from './admin';
import Fournisseur from './fournisseur';
import Article from './article';
import Projet from './projet';
import Commande from './commande';
import FournisseurArticle from './fournisseur-article';
import Frais from './frais';
import ArticleProjet from './article-projet';
import Paiement from './paiement';
import Client from './client';
import Entreprise from './entreprise';
import ResponsableProjet from './responsable-projet';
import SousAdmin from './sous-admin';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default ({ match }) => {
  return (
    <div>
      <Switch>
        {/* prettier-ignore */}
        <ErrorBoundaryRoute path={`${match.url}admin`} component={Admin} />
        <ErrorBoundaryRoute path={`${match.url}fournisseur`} component={Fournisseur} />
        <ErrorBoundaryRoute path={`${match.url}article`} component={Article} />
        <ErrorBoundaryRoute path={`${match.url}projet`} component={Projet} />
        <ErrorBoundaryRoute path={`${match.url}commande`} component={Commande} />
        <ErrorBoundaryRoute path={`${match.url}fournisseur-article`} component={FournisseurArticle} />
        <ErrorBoundaryRoute path={`${match.url}frais`} component={Frais} />
        <ErrorBoundaryRoute path={`${match.url}article-projet`} component={ArticleProjet} />
        <ErrorBoundaryRoute path={`${match.url}paiement`} component={Paiement} />
        <ErrorBoundaryRoute path={`${match.url}client`} component={Client} />
        <ErrorBoundaryRoute path={`${match.url}entreprise`} component={Entreprise} />
        <ErrorBoundaryRoute path={`${match.url}responsable-projet`} component={ResponsableProjet} />
        <ErrorBoundaryRoute path={`${match.url}sous-admin`} component={SousAdmin} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </Switch>
    </div>
  );
};
