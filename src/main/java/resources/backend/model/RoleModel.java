// package resources.backend.model;

// import jakarta.persistence.*;

// @Entity
// @Table(name = "roles")
// public class RoleModel {
//   @Id
//   @GeneratedValue(strategy = GenerationType.IDENTITY)
//   private Integer id;

//   @Enumerated(EnumType.STRING)
//   @Column(length = 20)
//   private ERoleModel name;

//   public RoleModel() {

//   }

//   public RoleModel(ERoleModel name) {
//     this.name = name;
//   }

//   public Integer getId() {
//     return id;
//   }

//   public void setId(Integer id) {
//     this.id = id;
//   }

//   public ERoleModel getName() {
//     return name;
//   }

//   public void setName(ERoleModel name) {
//     this.name = name;
//   }
// }