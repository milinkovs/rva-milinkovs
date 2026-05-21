package com.bolnica.project.services;

import java.util.List;

import com.bolnica.project.models.Dijagnoza;
import com.bolnica.project.models.Odeljenje;
import com.bolnica.project.models.Pacijent;

public interface PacijentService extends CrudService<Pacijent> {
	
	List<Pacijent> getByOsiguranje(boolean zdr_osiguranje);
	
	List<Pacijent> getPacijentsByDijagnoza(Dijagnoza dijagnoza);
	List<Pacijent> getPacijentsByOdeljenje(Odeljenje odeljenje);
}
