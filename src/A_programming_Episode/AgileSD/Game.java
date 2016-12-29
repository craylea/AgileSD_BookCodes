package A_programming_Episode.AgileSD;

public class Game {
	// �鼮������0�� ������Ϊ������1�����ʣ���Ϊ�������ܱ�֤������first throw or second throw
	// ��Ӧ��itsCurrentFrame���Ǻ��ʵ�ֵ��
	private int itsCurrentFrame = 1; 
	private boolean firstThrowInFrame = true;
	private Scorer itsScorer = new Scorer();
	
	public int getScore(){
//		return scoreForFrame(getCurrentFrame() - 1);
//		return scoreForFrame(getCurrentFrame());
		return scoreForFrame(itsCurrentFrame);
	}
	
	public int scoreForFrame(int frame) {
		return itsScorer.scoreForFrame(frame);
	}

	public void add(int pins){
		itsScorer.add(pins);
		adjustCurrentFrame(pins);
	}

	private void adjustCurrentFrame(int pins) {
//		if(firstThrowInFrame){
//			if(pins == 10){
//				itsCurrentFrame++;
//			}else{
//				firstThrowInFrame = false;
//			}
//			
//			
//		}else{
//			firstThrowInFrame = true;
//			itsCurrentFrame++;
//		}
//		itsCurrentFrame = Math.min(11, itsCurrentFrame);
		
//		if (firstThrowInFrame) {
//			if (adjustFrameForStrike(pins) == false) {
//				firstThrowInFrame = false;
//			}
//		} else {
//			firstThrowInFrame = true;
//			advanceFrame();
//		}
		
		if(lastBallInFrame(pins)){
			advanceFrame();
			// �鼮����û�����д���,�о�������,itsCurrentFrame�᲻�ϵ����ӣ�
			// ���ֳ�����ֵ������Frame������ball
			firstThrowInFrame = true; 
		}else{
			firstThrowInFrame = false;
			
		}
	}

	private boolean lastBallInFrame(int pins) {
		return strike(pins) || !firstThrowInFrame;
	}

	private boolean strike(int pins) {
		return (firstThrowInFrame && pins == 10);
	}

	private boolean adjustFrameForStrike(int pins){
		if(pins == 10){
			advanceFrame();
			return true;
		}
		return false;
	}
	
	private void advanceFrame() {
		itsCurrentFrame = Math.min(10, itsCurrentFrame + 1);;
	}

	public int getCurrentFrame() {
		return itsCurrentFrame;
	}
}
