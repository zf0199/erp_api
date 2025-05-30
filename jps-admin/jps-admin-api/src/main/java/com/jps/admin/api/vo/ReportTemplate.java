package com.jps.admin.api.vo;

import lombok.*;

import java.util.*;

@Data
@Builder
public class ReportTemplate {
	@Builder.Default
	private String ReportVersion = "1.0.";
	@Builder.Default
	private String ReportGuid = generateGuid();
	private String ReportName = "test";
	private String ReportAlias = "test";
	@Builder.Default
	private String ReportCreated = generateDotNetDateFormat(new Date());
	private String ReportChanged ;
	private String EngineVersion = "EngineV2";
	private String ReportUnit = "Centimeters";
	private String CalculationMode = "Interpretation";
	@Builder.Default
	private Dictionary Dictionary = new Dictionary();
	@Builder.Default
	private Page pages = initPage();

	@Data
	public static class Dictionary {

		private Map<String, Variable> variables = new HashMap<>();
		public Dictionary() {
			Variable var = new Variable();
			var.setValue("89745");
			var.setName("varId");
			var.setType("System.String");
			variables.put("0", var);
		}

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



		public static String generateGuid() {
			return UUID.randomUUID().toString().replaceAll("-", "").toLowerCase();
		}

		public static String generateDotNetDateFormat(Date date) {
			long timestamp = date.getTime();

			// 获取当前时区偏移（+0800）
			TimeZone tz = TimeZone.getDefault();
			int offsetInMillis = tz.getOffset(timestamp);
			int hours = offsetInMillis / (1000 * 60 * 60);
			int minutes = Math.abs((offsetInMillis / (1000 * 60)) % 60);
			String sign = hours >= 0 ? "+" : "-";

			String timezoneOffset = String.format("%s%02d%02d", sign, Math.abs(hours), minutes);

			return String.format("/Date(%d%s)/", timestamp, timezoneOffset);
		}




	private static Page initPage() {
		Page page = new Page();
		page.setIdent("StiPage");
		page.setName("Page1");
		page.setGuid("997a1f11edd4d73c4705d8734271aeee");
		Interaction interaction = new Interaction();
		interaction.setIdent("StiInteraction");
		page.setInteraction(interaction);
		page.setBorder(";;2;;;;;solid:Black");
		page.setBrush("solid:");
		Watermark watermark = new Watermark();
		watermark.setTextBrush("solid:50,0,0,0");
		page.setWatermark(watermark);
		return page;
	}

}
