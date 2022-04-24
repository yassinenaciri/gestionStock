import { IAdmin } from 'app/shared/model/admin.model';
import { ISousAdmin } from 'app/shared/model/sous-admin.model';

export interface IEntreprise {
  id?: number;
  nom?: string | null;
  admins?: IAdmin[] | null;
  sousAdmins?: ISousAdmin[] | null;
}

export const defaultValue: Readonly<IEntreprise> = {};
