package com.library.repository;

import com.library.entity.BookEntity;
import com.library.entity.BookManagementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.metamodel.Metamodel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface BookmanagementRepository extends JpaRepository<BookManagementEntity, Long> {


//    BookManagementEntity findByStudentCodeaAndBookEntityId(String studentCode);

    BookManagementEntity findByUsernameAndBookEntity_Id(String studentCode, Long bookId);

    EntityManager entityManager = new EntityManager() {
        @Override
        public void persist(Object entity) {

        }

        @Override
        public <T> T merge(T entity) {
            return null;
        }

        @Override
        public void remove(Object entity) {

        }

        @Override
        public <T> T find(Class<T> entityClass, Object primaryKey) {
            return null;
        }

        @Override
        public <T> T find(Class<T> entityClass, Object primaryKey, Map<String, Object> properties) {
            return null;
        }

        @Override
        public <T> T find(Class<T> entityClass, Object primaryKey, LockModeType lockMode) {
            return null;
        }

        @Override
        public <T> T find(Class<T> entityClass, Object primaryKey, LockModeType lockMode, Map<String, Object> properties) {
            return null;
        }

        @Override
        public <T> T getReference(Class<T> entityClass, Object primaryKey) {
            return null;
        }

        @Override
        public void flush() {

        }

        @Override
        public void setFlushMode(FlushModeType flushMode) {

        }

        @Override
        public FlushModeType getFlushMode() {
            return null;
        }

        @Override
        public void lock(Object entity, LockModeType lockMode) {

        }

        @Override
        public void lock(Object entity, LockModeType lockMode, Map<String, Object> properties) {

        }

        @Override
        public void refresh(Object entity) {

        }

        @Override
        public void refresh(Object entity, Map<String, Object> properties) {

        }

        @Override
        public void refresh(Object entity, LockModeType lockMode) {

        }

        @Override
        public void refresh(Object entity, LockModeType lockMode, Map<String, Object> properties) {

        }

        @Override
        public void clear() {

        }

        @Override
        public void detach(Object entity) {

        }

        @Override
        public boolean contains(Object entity) {
            return false;
        }

        @Override
        public LockModeType getLockMode(Object entity) {
            return null;
        }

        @Override
        public void setProperty(String propertyName, Object value) {

        }

        @Override
        public Map<String, Object> getProperties() {
            return null;
        }

        @Override
        public javax.persistence.Query createQuery(String qlString) {
            return null;
        }

        @Override
        public <T> TypedQuery<T> createQuery(CriteriaQuery<T> criteriaQuery) {
            return null;
        }

        @Override
        public javax.persistence.Query createQuery(CriteriaUpdate updateQuery) {
            return null;
        }

        @Override
        public javax.persistence.Query createQuery(CriteriaDelete deleteQuery) {
            return null;
        }

        @Override
        public <T> TypedQuery<T> createQuery(String qlString, Class<T> resultClass) {
            return null;
        }

        @Override
        public javax.persistence.Query createNamedQuery(String name) {
            return null;
        }

        @Override
        public <T> TypedQuery<T> createNamedQuery(String name, Class<T> resultClass) {
            return null;
        }

        @Override
        public javax.persistence.Query createNativeQuery(String sqlString) {
            return null;
        }

        @Override
        public javax.persistence.Query createNativeQuery(String sqlString, Class resultClass) {
            return null;
        }

        @Override
        public javax.persistence.Query createNativeQuery(String sqlString, String resultSetMapping) {
            return null;
        }

        @Override
        public StoredProcedureQuery createNamedStoredProcedureQuery(String name) {
            return null;
        }

        @Override
        public StoredProcedureQuery createStoredProcedureQuery(String procedureName) {
            return null;
        }

        @Override
        public StoredProcedureQuery createStoredProcedureQuery(String procedureName, Class... resultClasses) {
            return null;
        }

        @Override
        public StoredProcedureQuery createStoredProcedureQuery(String procedureName, String... resultSetMappings) {
            return null;
        }

        @Override
        public void joinTransaction() {

        }

        @Override
        public boolean isJoinedToTransaction() {
            return false;
        }

        @Override
        public <T> T unwrap(Class<T> cls) {
            return null;
        }

        @Override
        public Object getDelegate() {
            return null;
        }

        @Override
        public void close() {

        }

        @Override
        public boolean isOpen() {
            return false;
        }

        @Override
        public EntityTransaction getTransaction() {
            return null;
        }

        @Override
        public EntityManagerFactory getEntityManagerFactory() {
            return null;
        }

        @Override
        public CriteriaBuilder getCriteriaBuilder() {
            return null;
        }

        @Override
        public Metamodel getMetamodel() {
            return null;
        }

        @Override
        public <T> EntityGraph<T> createEntityGraph(Class<T> rootType) {
            return null;
        }

        @Override
        public EntityGraph<?> createEntityGraph(String graphName) {
            return null;
        }

        @Override
        public EntityGraph<?> getEntityGraph(String graphName) {
            return null;
        }

        @Override
        public <T> List<EntityGraph<? super T>> getEntityGraphs(Class<T> entityClass) {
            return null;
        }
    };

    @Query(nativeQuery = true, value = "select * from bookmanagement where user_name = :name")
    ArrayList<BookManagementEntity> findAllByStudentCode(@Param("name") String username);


    @Query("SELECT b FROM BookEntity b INNER JOIN BookManagementEntity be ON be.bookEntity.id = b.id "
            +"WHERE be.status= :status AND be.username = :name")
    List<BookEntity> getBookByStatus(@Param("name") String username,@Param("status") String status);

    @Query("SELECT b.name FROM BookEntity b INNER JOIN BookManagementEntity be ON be.bookEntity.id = b.id "
            +"WHERE be.status= 'BORROW' AND be.username = :name")
    List<String> getBookNameByUser(@Param("name") String username);

//    @Query("SELECT b FROM BookEntity b INNER JOIN BookManagementEntity be ON be.bookEntity.id = b.id "
//            +"WHERE be.status='REGISTER' AND be.username = :name")
//    List<BookEntity> getBookRegister(@Param("name") String username);

}
