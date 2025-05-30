package com.jps.tpms.api.vo;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Data
@RequiredArgsConstructor
public class ReportTemplate{
	private String ReportVersion;
	private String ReportGuid;
	private String ReportName;
	private String ReportAlias;
	private String ReportCreated;
	private String ReportChanged;
	private String EngineVersion;
	private String ReportUnit;
	private String CalculationMode;
	private Dictionary Dictionary;
	private Map<String, Page> Pages;

	@Data
	public static class Dictionary {
		private Map<String, Variable> Variables;
	}

	@Data
	public static class Variable {
		private String Value;
		private String Name;
		private String Type;
		private String Alias;
	}

	@Data
	public static class Page {
		private String Ident;
		private String Name;
		private String Guid;
		private Interaction Interaction;
		private String Border;
		private String Brush;
		private Watermark Watermark;
	}

	@Data
	public static class Interaction {
		private String Ident;
	}

	@Data
	public static class Watermark {
		private String TextBrush;
	}
}
