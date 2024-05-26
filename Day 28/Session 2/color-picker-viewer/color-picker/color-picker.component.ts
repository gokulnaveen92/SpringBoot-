import { ChangeDetectionStrategy, Component, EventEmitter, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-color-picker',
  templateUrl: './color-picker.component.html',
  styleUrls: ['./color-picker.component.css']
})
export class ColorPickerComponent implements OnInit {

  selectedColor:string= "#ff0000";
  @Output() colorSelected:EventEmitter<String> = new EventEmitter();

  constructor() { }

  ngOnInit(): void {
  }

  onColorChange(){
    this.colorSelected.emit(this.selectedColor);
  }



}
