package ua.leonidius.raytracing.material;

import ua.leonidius.raytracing.entities.ISpectrum;
import ua.leonidius.raytracing.entities.Vector3;

public interface IBRDF {

    ISpectrum reflect(ISpectrum incident, Vector3 incidentDirection, Vector3 outgoingDirection, Vector3 normal);

}
