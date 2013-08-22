public aspect ShowMethodCalls {
    pointcut methods() : execution(public * *(..)) && (within(com.sampullara.nib.tool.*));

    before() : methods() {
        System.out.println(thisJoinPointStaticPart);
    }
}