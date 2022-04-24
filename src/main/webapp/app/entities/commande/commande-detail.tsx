import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './commande.reducer';

export const CommandeDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const commandeEntity = useAppSelector(state => state.commande.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="commandeDetailsHeading">Commande</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{commandeEntity.id}</dd>
          <dt>
            <span id="nom">Nom</span>
          </dt>
          <dd>{commandeEntity.nom}</dd>
          <dt>
            <span id="description">Description</span>
          </dt>
          <dd>{commandeEntity.description}</dd>
          <dt>
            <span id="recu">Recu</span>
          </dt>
          <dd>{commandeEntity.recu ? 'true' : 'false'}</dd>
          <dt>
            <span id="quantite">Quantite</span>
          </dt>
          <dd>{commandeEntity.quantite}</dd>
          <dt>Items</dt>
          <dd>
            {commandeEntity.items
              ? commandeEntity.items.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {commandeEntity.items && i === commandeEntity.items.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/commande" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/commande/${commandeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default CommandeDetail;
