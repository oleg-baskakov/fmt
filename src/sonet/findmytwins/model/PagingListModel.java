package sonet.findmytwins.model;

import java.util.ArrayList;
import java.util.List;

import sonet.findmytwins.processors.DBProcessor;



public class PagingListModel extends AbstractPagingListModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4370353438941246687L;
	private ArrayList answers;
	private int power;

	
	public PagingListModel(int startPageNumber, int pageSize) {
		super(startPageNumber, pageSize);
	}

	@Override
	protected List getPageData(int itemStartNumber, int pageSize) {
		return null;//DatabaseInformation.dao.selectWithLimit(itemStartNumber, pageSize);
	}

	@Override
	public int getTotalSize() {
		return DBProcessor.getTwinsTotal(answers, power);
	}

}
