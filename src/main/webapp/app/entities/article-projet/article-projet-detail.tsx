import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './article-projet.reducer';

export const ArticleProjetDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const articleProjetEntity = useAppSelector(state => state.articleProjet.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="articleProjetDetailsHeading">ArticleProjet</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{articleProjetEntity.id}</dd>
          <dt>
            <span id="nom">Nom</span>
          </dt>
          <dd>{articleProjetEntity.nom}</dd>
          <dt>
            <span id="description">Description</span>
          </dt>
          <dd>{articleProjetEntity.description}</dd>
          <dt>
            <span id="vendu">Vendu</span>
          </dt>
          <dd>{articleProjetEntity.vendu ? 'true' : 'false'}</dd>
          <dt>Client</dt>
          <dd>{articleProjetEntity.client ? articleProjetEntity.client.id : ''}</dd>
          <dt>Projet</dt>
          <dd>{articleProjetEntity.projet ? articleProjetEntity.projet.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/article-projet" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/article-projet/${articleProjetEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ArticleProjetDetail;
