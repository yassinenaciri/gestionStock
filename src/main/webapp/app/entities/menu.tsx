import React from 'react';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/admin">
        Admin
      </MenuItem>
      <MenuItem icon="asterisk" to="/fournisseur">
        Fournisseur
      </MenuItem>
      <MenuItem icon="asterisk" to="/article">
        Article
      </MenuItem>
      <MenuItem icon="asterisk" to="/projet">
        Projet
      </MenuItem>
      <MenuItem icon="asterisk" to="/commande">
        Commande
      </MenuItem>
      <MenuItem icon="asterisk" to="/fournisseur-article">
        Fournisseur Article
      </MenuItem>
      <MenuItem icon="asterisk" to="/frais">
        Frais
      </MenuItem>
      <MenuItem icon="asterisk" to="/article-projet">
        Article Projet
      </MenuItem>
      <MenuItem icon="asterisk" to="/paiement">
        Paiement
      </MenuItem>
      <MenuItem icon="asterisk" to="/client">
        Client
      </MenuItem>
      <MenuItem icon="asterisk" to="/entreprise">
        Entreprise
      </MenuItem>
      <MenuItem icon="asterisk" to="/responsable-projet">
        Responsable Projet
      </MenuItem>
      <MenuItem icon="asterisk" to="/sous-admin">
        Sous Admin
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu as React.ComponentType<any>;
