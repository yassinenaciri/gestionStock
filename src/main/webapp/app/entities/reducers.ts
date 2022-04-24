import admin from 'app/entities/admin/admin.reducer';
import fournisseur from 'app/entities/fournisseur/fournisseur.reducer';
import article from 'app/entities/article/article.reducer';
import projet from 'app/entities/projet/projet.reducer';
import commande from 'app/entities/commande/commande.reducer';
import fournisseurArticle from 'app/entities/fournisseur-article/fournisseur-article.reducer';
import frais from 'app/entities/frais/frais.reducer';
import articleProjet from 'app/entities/article-projet/article-projet.reducer';
import paiement from 'app/entities/paiement/paiement.reducer';
import client from 'app/entities/client/client.reducer';
import entreprise from 'app/entities/entreprise/entreprise.reducer';
import responsableProjet from 'app/entities/responsable-projet/responsable-projet.reducer';
import sousAdmin from 'app/entities/sous-admin/sous-admin.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  admin,
  fournisseur,
  article,
  projet,
  commande,
  fournisseurArticle,
  frais,
  articleProjet,
  paiement,
  client,
  entreprise,
  responsableProjet,
  sousAdmin,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
