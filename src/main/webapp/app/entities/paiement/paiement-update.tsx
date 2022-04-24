import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IArticleProjet } from 'app/shared/model/article-projet.model';
import { getEntities as getArticleProjets } from 'app/entities/article-projet/article-projet.reducer';
import { IPaiement } from 'app/shared/model/paiement.model';
import { getEntity, updateEntity, createEntity, reset } from './paiement.reducer';

export const PaiementUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const articleProjets = useAppSelector(state => state.articleProjet.entities);
  const paiementEntity = useAppSelector(state => state.paiement.entity);
  const loading = useAppSelector(state => state.paiement.loading);
  const updating = useAppSelector(state => state.paiement.updating);
  const updateSuccess = useAppSelector(state => state.paiement.updateSuccess);
  const handleClose = () => {
    props.history.push('/paiement');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getArticleProjets({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...paiementEntity,
      ...values,
      item: articleProjets.find(it => it.id.toString() === values.item.toString()),
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
          ...paiementEntity,
          item: paiementEntity?.item?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="gestionStockApp.paiement.home.createOrEditLabel" data-cy="PaiementCreateUpdateHeading">
            Create or edit a Paiement
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="paiement-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Nom" id="paiement-nom" name="nom" data-cy="nom" type="text" />
              <ValidatedField label="Prenom" id="paiement-prenom" name="prenom" data-cy="prenom" type="text" />
              <ValidatedField label="Mail" id="paiement-mail" name="mail" data-cy="mail" type="text" />
              <ValidatedField id="paiement-item" name="item" data-cy="item" label="Item" type="select">
                <option value="" key="0" />
                {articleProjets
                  ? articleProjets.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/paiement" replace color="info">
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

export default PaiementUpdate;
