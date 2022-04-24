import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IArticle } from 'app/shared/model/article.model';
import { getEntities as getArticles } from 'app/entities/article/article.reducer';
import { IResponsableProjet } from 'app/shared/model/responsable-projet.model';
import { getEntities as getResponsableProjets } from 'app/entities/responsable-projet/responsable-projet.reducer';
import { IProjet } from 'app/shared/model/projet.model';
import { getEntity, updateEntity, createEntity, reset } from './projet.reducer';

export const ProjetUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const articles = useAppSelector(state => state.article.entities);
  const responsableProjets = useAppSelector(state => state.responsableProjet.entities);
  const projetEntity = useAppSelector(state => state.projet.entity);
  const loading = useAppSelector(state => state.projet.loading);
  const updating = useAppSelector(state => state.projet.updating);
  const updateSuccess = useAppSelector(state => state.projet.updateSuccess);
  const handleClose = () => {
    props.history.push('/projet');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getArticles({}));
    dispatch(getResponsableProjets({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...projetEntity,
      ...values,
      articles: mapIdList(values.articles),
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
          ...projetEntity,
          articles: projetEntity?.articles?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="gestionStockApp.projet.home.createOrEditLabel" data-cy="ProjetCreateUpdateHeading">
            Create or edit a Projet
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="projet-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Nom" id="projet-nom" name="nom" data-cy="nom" type="text" />
              <ValidatedField label="Description" id="projet-description" name="description" data-cy="description" type="text" />
              <ValidatedField label="Articles" id="projet-articles" data-cy="articles" type="select" multiple name="articles">
                <option value="" key="0" />
                {articles
                  ? articles.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/projet" replace color="info">
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

export default ProjetUpdate;
