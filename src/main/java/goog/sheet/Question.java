package goog.sheet;

import java.io.Serializable;
import java.util.Objects;

public class Question implements Serializable {

	private static final long serialVersionUID = 357588208329285250L;

	private String id;

	private String question;

	private String answerType;

	private String answer;

	public Question(String id, String question, String answerType, String answer) {
		super();
		this.id = id;
		this.question = question;
		this.answerType = answerType;
		this.answer = answer;
	}

	public Question() {
		super();
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswerType() {
		return answerType;
	}

	public void setAnswerType(String answerType) {
		this.answerType = answerType;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, question);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Question other = (Question) obj;
		return Objects.equals(id, other.id) && Objects.equals(question, other.question);
	}

}
