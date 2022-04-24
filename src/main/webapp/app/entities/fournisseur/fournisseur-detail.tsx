import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './fournisseur.reducer';

export const FournisseurDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const fournisseurEntity = useAppSelector(state => state.fournisseur.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="fournisseurDetailsHeading">Fournisseur</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{fournisseurEntity.id}</dd>
          <dt>
            <span id="nom">Nom</span>
          </dt>
          <dd>{fournisseurEntity.nom}</dd>
          <dt>
            <span id="ice">Ice</span>
          </dt>
          <dd>{fournisseurEntity.ice}</dd>
          <dt>
            <span id="tel">Tel</span>
          </dt>
          <dd>{fournisseurEntity.tel}</dd>
          <dt>
            <span id="adresse">Adresse</span>
          </dt>
          <dd>{fournisseurEntity.adresse}</dd>
          <dt>
            <span id="description">Description</span>
          </dt>
          <dd>{fournisseurEntity.description}</dd>
        </dl>
        <Button tag={Link} to="/fournisseur" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/fournisseur/${fournisseurEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default FournisseurDetail;
