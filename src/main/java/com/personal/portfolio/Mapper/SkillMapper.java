package com.personal.portfolio.Mapper;


import com.personal.portfolio.Dto.SkillDTO;
import com.personal.portfolio.Model.Skill;

public class SkillMapper {

    public static SkillDTO toDTO(Skill skill) {
        SkillDTO dto = new SkillDTO();
        dto.setSkillId(skill.getSkillId());
        dto.setName(skill.getName());
        dto.setCategory(skill.getCategory());
        return dto;
    }

    public static Skill toEntity(SkillDTO dto) {
        Skill skill = new Skill();
        skill.setSkillId(dto.getSkillId());
        skill.setName(dto.getName());
        skill.setCategory(dto.getCategory());
        return skill;
    }
}
