import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './projet.reducer';

export const ProjetDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const projetEntity = useAppSelector(state => state.projet.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="projetDetailsHeading">Projet</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{projetEntity.id}</dd>
          <dt>
            <span id="nom">Nom</span>
          </dt>
          <dd>{projetEntity.nom}</dd>
          <dt>
            <span id="description">Description</span>
          </dt>
          <dd>{projetEntity.description}</dd>
          <dt>Articles</dt>
          <dd>
            {projetEntity.articles
              ? projetEntity.articles.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {projetEntity.articles && i === projetEntity.articles.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/projet" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/projet/${projetEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProjetDetail;
