package com.company.stuff.iam.usermanagement

import org.springframework.data.jpa.repository.JpaRepository
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Dao(@Id val str: String)

interface DaoRepository : JpaRepository<Dao, String>
