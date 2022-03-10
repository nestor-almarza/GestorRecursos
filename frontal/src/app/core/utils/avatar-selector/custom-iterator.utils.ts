export class CustomIterator {
    iterable: Array<any>;
    currentIndex = 0

    constructor(iterable: Array<any>){
        this.iterable = iterable;
    }

    next() {
        this.currentIndex++
        if(this.currentIndex == this.iterable.length)
            this.currentIndex = 0;

        return this.getCurrentValue()
    }

    back(){
        this.currentIndex--
        if(this.currentIndex < 0)
            this.currentIndex = this.iterable.length - 1;

        return this.getCurrentValue()
    }

    random(){
        this.currentIndex = Math.floor(Math.random() * this.iterable.length);
        return this.getCurrentValue();
    }

    findAndSetIndex(value: any){
        this.currentIndex = this.iterable.findIndex(item => value == item);
    }

    getCurrentValue(){
        return this.iterable[this.currentIndex];
    }
}