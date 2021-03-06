package com.fterecorder.app.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fterecorder.app.dao.FTERecordSequenceDao;
import com.fterecorder.app.dao.FTERecorderRepo;
import com.fterecorder.app.dao.FTERecordsDao;
import com.fterecorder.app.model.FTERecord;

@Service
public class FTERecorderServiceImpl implements FTERecorderService {

	private static final String FTEREC_SEQ_KEY = "fterec";

	@Autowired
	FTERecorderRepo repo;

	@Autowired
	FTERecordSequenceDao seq;
	
	@Autowired
	FTERecordsDao fteDao;

	@Override
	public boolean insertRecords(List<FTERecord> records) {

		records.forEach(r -> {
			r.setId(seq.getNextFteRecSequenceId(FTEREC_SEQ_KEY));
			r.setCreateDt(LocalDateTime.now());
			r.setWeekStDt(r.getWeekStDt().plusDays(1));
			r.setWeekEdDt(r.getWeekEdDt().plusDays(1));
		});

		try {
			repo.saveAll(records);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteById(Long id) {
		try {
			fteDao.deleteRecordsById(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
}
