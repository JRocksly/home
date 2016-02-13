package jrocksly.project.dao;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import jrocksly.project.model.Outgoing;

@Transactional
public interface OutgoingRepository extends CrudRepository<Outgoing, Long> {}
