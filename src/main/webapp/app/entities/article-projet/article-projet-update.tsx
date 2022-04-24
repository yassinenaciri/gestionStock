import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IClient } from 'app/shared/model/client.model';
import { getEntities as getClients } from 'app/entities/client/client.reducer';
import { IProjet } from 'app/shared/model/projet.model';
import { getEntities as getProjets } from 'app/entities/projet/projet.reducer';
import { IArticleProjet } from 'app/shared/model/article-projet.model';
import { getEntity, updateEntity, createEntity, reset } from './article-projet.reducer';

export const ArticleProjetUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const clients = useAppSelector(state => state.client.entities);
  const projets = useAppSelector(state => state.projet.entities);
  const articleProjetEntity = useAppSelector(state => state.articleProjet.entity);
  const loading = useAppSelector(state => state.articleProjet.loading);
  const updating = useAppSelector(state => state.articleProjet.updating);
  const updateSuccess = useAppSelector(state => state.articleProjet.updateSuccess);
  const handleClose = () => {
    props.history.push('/article-projet');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getClients({}));
    dispatch(getProjets({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...articleProjetEntity,
      ...values,
      client: clients.find(it => it.id.toString() === values.client.toString()),
      projet: projets.find(it => it.id.toString() === values.projet.toString()),
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
          ...articleProjetEntity,
          client: articleProjetEntity?.client?.id,
          projet: articleProjetEntity?.projet?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="gestionStockApp.articleProjet.home.createOrEditLabel" data-cy="ArticleProjetCreateUpdateHeading">
            Create or edit a ArticleProjet
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField name="id" required readOnly id="article-projet-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Nom" id="article-projet-nom" name="nom" data-cy="nom" type="text" />
              <ValidatedField label="Description" id="article-projet-description" name="description" data-cy="description" type="text" />
              <ValidatedField label="Vendu" id="article-projet-vendu" name="vendu" data-cy="vendu" check type="checkbox" />
              <ValidatedField id="article-projet-client" name="client" data-cy="client" label="Client" type="select">
                <option value="" key="0" />
                {clients
                  ? clients.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="article-projet-projet" name="projet" data-cy="projet" label="Projet" type="select">
                <option value="" key="0" />
                {projets
                  ? projets.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/article-projet" replace color="info">
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

export default ArticleProjetUpdate;
