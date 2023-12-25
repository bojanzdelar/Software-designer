import type RequirementType from "./RequirementType";
import type RequirementPriority from "./RequirementPriority";
import type RequirementRisk from "./RequirementRisk";
import type RequirementStatus from "./RequirementStatus";

export default interface Requirement {
  id?: number;
  project?: string;
  title?: string;
  description?: string;
  type?: RequirementType;
  priority?: RequirementPriority;
  stakeholders?: string;
  risk?: RequirementRisk;
  status?: RequirementStatus;
  effortAssessment?: string;
}
