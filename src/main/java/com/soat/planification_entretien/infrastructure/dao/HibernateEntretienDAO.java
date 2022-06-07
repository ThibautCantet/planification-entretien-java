package com.soat.planification_entretien.infrastructure.dao;

import java.time.LocalDateTime;
import java.util.List;

import com.soat.planification_entretien.domain.candidat.entity.Candidat;
import com.soat.planification_entretien.domain.entretien.command.entity.Calendrier;
import com.soat.planification_entretien.domain.entretien.command.entity.RendezVous;
import com.soat.planification_entretien.domain.entretien.listener.dao.EntretienDAO;
import com.soat.planification_entretien.domain.entretien.listener.dto.Entretien;
import com.soat.planification_entretien.domain.entretien.query.dto.IEntretien;
import com.soat.planification_entretien.domain.recruteur.command.entity.Recruteur;
import com.soat.planification_entretien.domain.rendez_vous.command.repository.CalendrierRepository;
import com.soat.planification_entretien.infrastructure.repository.EntretienCrud;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateEntretienDAO implements EntretienDAO,
        com.soat.planification_entretien.domain.entretien.query.dao.EntretienDAO {
    private final EntretienCrud entretienCrud;
    private final CalendrierRepository calendrierRepository;

    public HibernateEntretienDAO(EntretienCrud entretienCrud, CalendrierRepository calendrierRepository) {
        this.entretienCrud = entretienCrud;
        this.calendrierRepository = calendrierRepository;
    }

    @Override
    public Entretien findById(int id) {
        return entretienCrud.findById(id).map(entretien -> new Entretien(entretien.getRecruteur().getEmail(), entretien.getCandidat().getEmail(), entretien.getHoraireEntretien()))
                .orElse(null);
    }

    @Override
    public List<IEntretien> findAll() {
        return entretienCrud.findAll().stream()
                .map(this::toEntretien)
                .toList();
    }

    private IEntretien toEntretien(com.soat.planification_entretien.infrastructure.repository.Entretien jpaEntretien) {
        List<RendezVous> rendezVous = calendrierRepository.findByRecruteur(jpaEntretien.getRecruteur().getEmail())
                .map(Calendrier::rendezVous)
                .orElse(List.of());

        return new IEntretienImpl(
                jpaEntretien.getId(),
                new Candidat(jpaEntretien.getId(), jpaEntretien.getCandidat().getLanguage(), jpaEntretien.getCandidat().getEmail(), jpaEntretien.getCandidat().getExperienceInYears()),
                new Recruteur(jpaEntretien.getId(), jpaEntretien.getRecruteur().getLanguage(), jpaEntretien.getRecruteur().getEmail(), jpaEntretien.getRecruteur().getExperienceInYears(), rendezVous),
                jpaEntretien.getHoraireEntretien());
    }

    class IEntretienImpl implements IEntretien {

        private Integer id;
        private Candidat candidat;
        private Recruteur recruteur;
        private LocalDateTime horaireEntretien;

        public IEntretienImpl(Integer id, Candidat candidat, Recruteur recruteur, LocalDateTime horaireEntretien) {
            this.id = id;
            this.candidat = candidat;
            this.recruteur = recruteur;
            this.horaireEntretien = horaireEntretien;
        }

        @Override
        public Integer getId() {
            return id;
        }

        @Override
        public String getEmailCandidat() {
            return candidat.getEmail();
        }

        @Override
        public String getEmailRecruteur() {
            return recruteur.getEmail();
        }

        @Override
        public String getLanguage() {
            return recruteur.getLanguage();
        }

        @Override
        public LocalDateTime getHoraire() {
            return horaireEntretien;
        }
    }
}
