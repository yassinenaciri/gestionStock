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
import { IFournisseur } from 'app/shared/model/fournisseur.model';
import { getEntities as getFournisseurs } from 'app/entities/fournisseur/fournisseur.reducer';
import { ICommande } from 'app/shared/model/commande.model';
import { getEntities as getCommandes } from 'app/entities/commande/commande.reducer';
import { IFournisseurArticle } from 'app/shared/model/fournisseur-article.model';
import { getEntity, updateEntity, createEntity, reset } from './fournisseur-article.reducer';

export const FournisseurArticleUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const articles = useAppSelector(state => state.article.entities);
  const fournisseurs = useAppSelector(state => state.fournisseur.entities);
  const commandes = useAppSelector(state => state.commande.entities);
  const fournisseurArticleEntity = useAppSelector(state => state.fournisseurArticle.entity);
  const loading = useAppSelector(state => state.fournisseurArticle.loading);
  const updating = useAppSelector(state => state.fournisseurArticle.updating);
  const updateSuccess = useAppSelector(state => state.fournisseurArticle.updateSuccess);
  const handleClose = () => {
    props.history.push('/fournisseur-article');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getArticles({}));
    dispatch(getFournisseurs({}));
    dispatch(getCommandes({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...fournisseurArticleEntity,
      ...values,
      article: articles.find(it => it.id.toString() === values.article.toString()),
      fournisseur: fournisseurs.find(it => it.id.toString() === values.fournisseur.toString()),
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
          ...fournisseurArticleEntity,
          article: fournisseurArticleEntity?.article?.id,
          fournisseur: fournisseurArticleEntity?.fournisseur?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="gestionStockApp.fournisseurArticle.home.createOrEditLabel" data-cy="FournisseurArticleCreateUpdateHeading">
            Create or edit a FournisseurArticle
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
                <ValidatedField name="id" required readOnly id="fournisseur-article-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Prix" id="fournisseur-article-prix" name="prix" data-cy="prix" type="text" />
              <ValidatedField id="fournisseur-article-article" name="article" data-cy="article" label="Article" type="select">
                <option value="" key="0" />
                {articles
                  ? articles.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="fournisseur-article-fournisseur"
                name="fournisseur"
                data-cy="fournisseur"
                label="Fournisseur"
                type="select"
              >
                <option value="" key="0" />
                {fournisseurs
                  ? fournisseurs.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/fournisseur-article" replace color="info">
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

export default FournisseurArticleUpdate;
