import { IPaiement } from 'app/shared/model/paiement.model';
import { IClient } from 'app/shared/model/client.model';
import { IProjet } from 'app/shared/model/projet.model';

export interface IArticleProjet {
  id?: number;
  nom?: string | null;
  description?: string | null;
  vendu?: boolean | null;
  paiements?: IPaiement[] | null;
  client?: IClient | null;
  projet?: IProjet | null;
}

export const defaultValue: Readonly<IArticleProjet> = {
  vendu: false,
};
