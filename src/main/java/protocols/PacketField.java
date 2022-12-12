package protocols;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PacketField {
    private byte fieldId;
    private byte fieldSize;
    private byte[] content;

    public PacketField(byte id) {
        this.fieldId = id;
    }
}