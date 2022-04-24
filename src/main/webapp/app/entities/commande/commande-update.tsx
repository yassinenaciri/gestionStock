import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IFournisseurArticle } from 'app/shared/model/fournisseur-article.model';
import { getEntities as getFournisseurArticles } from 'app/entities/fournisseur-article/fournisseur-article.reducer';
import { ICommande } from 'app/shared/model/commande.model';
import { getEntity, updateEntity, createEntity, reset } from './commande.reducer';

export const CommandeUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const fournisseurArticles = useAppSelector(state => state.fournisseurArticle.entities);
  const commandeEntity = useAppSelector(state => state.commande.entity);
  const loading = useAppSelector(state => state.commande.loading);
  const updating = useAppSelector(state => state.commande.updating);
  const updateSuccess = useAppSelector(state => state.commande.updateSuccess);
  const handleClose = () => {
    props.history.push('/commande');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getFournisseurArticles({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...commandeEntity,
      ...values,
      items: mapIdList(values.items),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...commandeEntity,
          items: commandeEntity?.items?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="gestionStockApp.commande.home.createOrEditLabel" data-cy="CommandeCreateUpdateHeading">
            Create or edit a Commande
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="commande-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Nom" id="commande-nom" name="nom" data-cy="nom" type="text" />
              <ValidatedField label="Description" id="commande-description" name="description" data-cy="description" type="text" />
              <ValidatedField label="Recu" id="commande-recu" name="recu" data-cy="recu" check type="checkbox" />
              <ValidatedField label="Quantite" id="commande-quantite" name="quantite" data-cy="quantite" type="text" />
              <ValidatedField label="Items" id="commande-items" data-cy="items" type="select" multiple name="items">
                <option value="" key="0" />
                {fournisseurArticles
                  ? fournisseurArticles.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/commande" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default CommandeUpdate;
